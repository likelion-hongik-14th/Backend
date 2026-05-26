package mutsa.week2.search;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mutsa.week2.global.apiPayload.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Search", description = "검색 API")
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    @Operation(summary = "상품 검색", description = "키워드(이름 부분 일치)로 상품을 검색합니다. 키워드는 비어있을 수 없습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "상품 검색 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SEARCH_4001", description = "검색 키워드는 비어있을 수 없습니다.")
    })
    public ApiResponse<SearchResponseDto> search(@RequestParam String keyword) {
        return ApiResponse.onSuccess("상품 검색 성공", searchService.searchByName(keyword));
    }

    @GetMapping("/popular")
    @Operation(summary = "인기 검색어 조회", description = "현재 시점의 인기 검색어 상위 목록을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "인기 검색어 조회 성공")
    })
    public ApiResponse<PopularSearchResponseDto> getPopularSearches() {
        return ApiResponse.onSuccess("인기 검색어 조회 성공", searchService.getPopularKeywords());
    }
}
