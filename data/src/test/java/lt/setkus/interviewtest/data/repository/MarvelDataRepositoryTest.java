package lt.setkus.interviewtest.data.repository;

import android.content.Context;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import lt.setkus.interviewtest.data.rest.RestClient;
import lt.setkus.interviewtest.data.rest.response.Comics;
import lt.setkus.interviewtest.domain.domain.ComicDomain;
import rx.observers.TestSubscriber;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class MarvelDataRepositoryTest {

    @Mock
    private Context context;

    private MockWebServer mockWebServer;

    private MarvelDataRepository marvelDataRepository;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        RestClient restClient = RestClient.newInstance(context);
        marvelDataRepository = new MarvelDataRepository(restClient);

        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testGetComics() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        TestSubscriber<List<ComicDomain>> testSubscriber = new TestSubscriber<>();
        marvelDataRepository.getComics(null).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
    }
}
