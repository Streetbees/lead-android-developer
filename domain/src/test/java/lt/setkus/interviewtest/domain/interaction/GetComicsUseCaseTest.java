package lt.setkus.interviewtest.domain.interaction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lt.setkus.interviewtest.domain.executor.PostExecutionThread;
import lt.setkus.interviewtest.domain.executor.ThreadExecutor;
import lt.setkus.interviewtest.domain.repository.MarvelRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class GetComicsUseCaseTest {

    private lt.setkus.interviewtest.domain.interaction.GetComicsUseCase getComicsUseCase;

    @Mock
    private ThreadExecutor threadExecutor;

    @Mock
    private PostExecutionThread postExecutionThread;

    @Mock
    private MarvelRepository marvelRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        getComicsUseCase = new lt.setkus.interviewtest.domain.interaction.GetComicsUseCase(threadExecutor, postExecutionThread, marvelRepository);
    }

    @Test
    public void testGetMarvelComicsHappyCase() {
        getComicsUseCase.buildUseCaseObservable();

        verify(marvelRepository).getComics(null);

        verifyNoMoreInteractions(marvelRepository);
        verifyZeroInteractions(threadExecutor);
        verifyZeroInteractions(postExecutionThread);
    }
}
