package com.luv2code.springbootlibrary.requestmodels;
import lombok.Data;
@Data
public class AdminQuestionRequest {
    private Long id; //this is going to be a message id
    private String response;
}

//This is going to be the object that we send from our React application to our back end application So we can update the current message with a response from the admin