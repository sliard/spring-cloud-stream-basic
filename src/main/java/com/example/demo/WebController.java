package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produce")
public class WebController {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping(value = "/{destination}")
    @ResponseBody
    public ResponseEntity<String> produceMessage(@PathVariable(name = "destination") String destination, @RequestBody Item item) {
        if ("buy".equals(destination)) {
            streamBridge.send("buyItem-out-0", item);
        } else {
            streamBridge.send("listItem-out-0", item);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
