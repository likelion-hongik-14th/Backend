package mutsa.week2.search;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<SearchResponseDto> search(@RequestParam String keyword) {
        return ResponseEntity.ok(searchService.searchByName(keyword));
    }

    @GetMapping("/popular")
    public ResponseEntity<PopularSearchResponseDto> getPopularSearches() {
        return ResponseEntity.ok(searchService.getPopularKeywords());
    }
}
