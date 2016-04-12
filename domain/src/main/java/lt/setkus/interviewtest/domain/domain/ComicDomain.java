package lt.setkus.interviewtest.domain.domain;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class ComicDomain {
    private String title;
    private String thumbnailPath;
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
