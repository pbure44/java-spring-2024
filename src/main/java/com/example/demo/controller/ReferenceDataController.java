package com.example.demo.controller;

import com.example.demo.properties.Office;
import com.example.demo.properties.ReferenceDataProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reference-data")
@RequiredArgsConstructor
public class ReferenceDataController {

    @Value("${reference-data.categories:}#{T(java.util.Collections).emptyList()}")
    private List<String> categories;

    private final ReferenceDataProperties referenceDataProperties;

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/offices")
    public ResponseEntity<List<Office>> getOffices(@RequestParam(required = false) String city) {
        if (city != null) {
            List<Office> result = referenceDataProperties
                    .getOffices()
                    .stream()
                    .filter(office -> Objects.equals(office.getAddress().getCity(), city))
                    .toList();
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.ok(referenceDataProperties.getOffices());
        }
    }

    @GetMapping("/offices/{name}")
    public ResponseEntity<Office> getOffice(@PathVariable String name) {
//        if (referenceDataProperties.getOffices() !=null) {
//            Optional<Office> result = referenceDataProperties
//                    .getOffices()
//                    .stream()
//                    .filter(office -> Objects.equals(office.getName(), name))
//                    .findFirst();
//
///*     -=Variations of expression instead of "return ResponseEntity.of(result);"=-
//    1) 1st option:
//        if (result.isPresent()) {
//            return ResponseEntity.ok(result.get());
//        }else {
//            return ResponseEntity.notFound().build();
//        }
//*/
//     2) 2nd option:
////        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//     3) 3rd option:
////        return result.isPresent() ? ResponseEntity.ok(result.get()) : ResponseEntity.notFound().build();
//
//            return ResponseEntity.of(result);
//
//        }else {
//            return ResponseEntity.notFound().build();
//        }

        Optional<Office> result = Optional.ofNullable(referenceDataProperties)
                .map(ReferenceDataProperties::getOffices)
                .stream()
                .flatMap(Collection::stream)
                .filter(office -> Objects.equals(office.getAddress().getCity(), name))
                .findFirst();

        return ResponseEntity.of(result);

    }
}
