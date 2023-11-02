package com.noty.web.controllers.api;

import com.noty.web.NotyException;
import com.noty.web.entities.NotyList;
import com.noty.web.services.ListProvider;
import com.noty.web.services.security.NotyImpersonation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/list")
@RequiredArgsConstructor
public class ListsApiController {

    private final ListProvider listProvider;

    @PostMapping("/")
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

}
