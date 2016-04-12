package lt.setkus.interviewtest.domain.interaction;

import java.util.List;

import lt.setkus.interviewtest.domain.repository.MarvelRepository;
import lt.setkus.interviewtest.domain.domain.ComicDomain;
import lt.setkus.interviewtest.domain.executor.PostExecutionThread;
import lt.setkus.interviewtest.domain.executor.ThreadExecutor;
import rx.Observable;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class GetComicsUseCase extends UseCase<List<ComicDomain>> {

    private final MarvelRepository marvelRepository;
    private Integer offset;

    public GetComicsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, MarvelRepository marvelRepository) {
        super(threadExecutor, postExecutionThread);
        this.marvelRepository = marvelRepository;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    protected Observable<List<ComicDomain>> buildUseCaseObservable() {
        return marvelRepository.getComics(offset);
    }
}
