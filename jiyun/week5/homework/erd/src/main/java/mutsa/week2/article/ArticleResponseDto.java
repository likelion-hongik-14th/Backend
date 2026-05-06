package mutsa.week2.article;

public record ArticleResponseDto(Long id, String title, String content) {
    public static ArticleResponseDto from(Article article) {
        return new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent());
    }
}
