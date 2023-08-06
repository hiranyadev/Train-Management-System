package com.org.project.TrainTicketingManagement.util;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {

	private Object payload = null;
    private List<String> messages = new ArrayList<>();
    private boolean status = false;
}
