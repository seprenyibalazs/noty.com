package com.noty.web.controllers.api;

import com.noty.web.NotyException;
import com.noty.web.controllers.api.model.requests.NotyListCreateModel;
import com.noty.web.controllers.api.model.response.NotyListResponse;
import com.noty.web.entities.NotyList;
import com.noty.web.services.ListProvider;
import com.noty.web.services.security.NotyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/list")
@RequiredArgsConstructor
public class ListsApiController {

    private final ListProvider listProvider;

    @PostMapping("/")
    public NotyListResponse createList(
            @AuthenticationPrincipal NotyUserDetails userDetails,
            @RequestBody NotyListCreateModel model
    ) throws NotyException {
        NotyList list = listProvider.createList(userDetails, model.getTitle());
        return NotyListResponse.fromList(list);
    }

}
