package com.noty.web.controllers.api;

import com.noty.web.NotyException;
import com.noty.web.controllers.api.model.response.NotyListResponse;
import com.noty.web.entities.NotyList;
import com.noty.web.services.ListProvider;
import com.noty.web.services.security.NotyImpersonation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/list")
@RequiredArgsConstructor
public class ListsApiController {

    private final ListProvider listProvider;

    @PostMapping("/")
    public NotyListResponse createList(
            @AuthenticationPrincipal NotyImpersonation impersonation,
            String title
    ) throws NotyException {
        NotyList list = listProvider.createList(impersonation, title);
        return NotyListResponse.fromList(list);
    }

}
