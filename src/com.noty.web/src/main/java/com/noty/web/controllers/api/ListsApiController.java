package com.noty.web.controllers.api;

import com.noty.web.NotyEntityNotFoundException;
import com.noty.web.NotyException;
import com.noty.web.controllers.api.model.response.EntryResponse;
import com.noty.web.controllers.api.model.response.NotyListResponse;
import com.noty.web.entities.Entry;
import com.noty.web.entities.NotyList;
import com.noty.web.services.ListProvider;
import com.noty.web.services.security.NotyImpersonation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

@RestController
@RequestMapping("/api/list")
@RequiredArgsConstructor
public class ListsApiController {

    private final ListProvider listProvider;

    @PostMapping()
    public ResponseEntity<?> createList(
            @AuthenticationPrincipal NotyImpersonation impersonation,
            UriComponentsBuilder uriBuilder,
            String title
    ) throws NotyException {
        NotyList list = listProvider.createList(impersonation, title);
        URI uri = uriBuilder.path("api/list/{id}")
                .buildAndExpand(list.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public NotyListResponse getList(
            @AuthenticationPrincipal NotyImpersonation impersonation,
            @PathVariable long id
    ) throws NotyException {
        NotyList list = listProvider.findById(id, true);
        impersonation.assertReadAccess(list);

        return NotyListResponse.fromList(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteList(
            @AuthenticationPrincipal NotyImpersonation impersonation,
            @PathVariable long id
    ) throws NotyException {
        NotyList list = listProvider.findById(id, true);
        impersonation.assertWriteAccess(list);

        listProvider.deleteList(list);

        return ResponseEntity.noContent().build();

    }

    @GetMapping()
    public NotyListResponse[] getAccessibleLists(
            @AuthenticationPrincipal NotyImpersonation impersonation
    ) {
        return Arrays.stream(listProvider.findAccessibleLists(impersonation.getId()))
                .map(NotyListResponse::fromList)
                .toArray(NotyListResponse[]::new);
    }

    @PostMapping("/{id}/entry")
    public ResponseEntity<?> createEntry(
            @AuthenticationPrincipal NotyImpersonation impersonation,
            UriComponentsBuilder uriBuilder,
            @PathVariable long id,
            String description
    ) throws NotyException {
        NotyList list = listProvider.findById(id, true);
        impersonation.hasReadAccess(list);

        Entry entry = listProvider.createEntry(impersonation.getId(), list, description);

        URI uri = uriBuilder.path("api/list/{listId}/entry/{entryId}")
                .buildAndExpand(list.getId(), entry.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{listId}/entry/{entryId}")
    public EntryResponse getEntry(
            @AuthenticationPrincipal NotyImpersonation impersonation,
            @PathVariable long listId,
            @PathVariable long entryId
    ) throws NotyException {
        NotyList list = listProvider.findById(listId, true);
        impersonation.assertReadAccess(list);

        Entry entry = listProvider.findEntryById(entryId, true);
        if (entry.getList() == null || entry.getList().getId() != list.getId())
            throw new NotyEntityNotFoundException("Entry was not found.");

        return EntryResponse.fromEntry(entry);
    }

    @DeleteMapping("/{listId}/entry/{entryId}")
    public ResponseEntity<?> deleteEntry(
            @AuthenticationPrincipal NotyImpersonation impersonation,
            @PathVariable long listId,
            @PathVariable long entryId
    ) throws NotyException {
        NotyList list = listProvider.findById(listId, true);
        impersonation.assertWriteAccess(list);

        Entry entry = listProvider.findEntryById(entryId, true);
        if (entry.getList() == null || entry.getList().getId() != list.getId())
            throw new NotyEntityNotFoundException("Entry was not found.");

        listProvider.deleteEntry(entry);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{listId}/entry/{entryId}")
    public ResponseEntity<?> updateEntry(
            @AuthenticationPrincipal NotyImpersonation impersonation,
            @PathVariable long listId,
            @PathVariable long entryId,
            Boolean done,
            String description
    ) throws NotyException {
        NotyList list = listProvider.findById(listId, true);
        impersonation.assertWriteAccess(list);

        Entry entry = listProvider.findEntryById(entryId, true);
        if (entry.getList() == null || entry.getList().getId() != list.getId())
            throw new NotyEntityNotFoundException("Entry was not found.");

        entry.trySetDescription(description);
        entry.trySetIsDone(done);

        listProvider.updateEntry(entry);

        return ResponseEntity.noContent().build();
    }

}
