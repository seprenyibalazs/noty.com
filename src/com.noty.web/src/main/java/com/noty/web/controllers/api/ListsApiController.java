package com.noty.web.controllers.api;

import com.noty.web.NotyException;
import com.noty.web.controllers.api.model.response.NotyListResponse;
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

}
