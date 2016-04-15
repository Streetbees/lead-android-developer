package lt.setkus.interviewtest.model;

import lt.setkus.interviewtest.domain.domain.ComicDomain;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class ComicModel {
    private String title;
    private String thumbnailPath;
    private long id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static ComicModel fromComicDomain(ComicDomain comicDomain) {
        ComicModel comicModel = new ComicModel();

        comicModel.setId(comicDomain.getId());
        comicModel.setThumbnailPath(comicDomain.getThumbnailPath());
        comicModel.setTitle(comicDomain.getTitle());

        return comicModel;
    }
}
