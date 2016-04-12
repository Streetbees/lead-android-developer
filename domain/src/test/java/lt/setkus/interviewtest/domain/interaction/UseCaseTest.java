package lt.setkus.interviewtest.domain.interaction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lt.setkus.interviewtest.domain.executor.PostExecutionThread;
import lt.setkus.interviewtest.domain.executor.ThreadExecutor;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class UseCaseTest {

    private TestUseCase testUseCase;

    @Mock
    private ThreadExecutor threadExecutor;

    @Mock
    private PostExecutionThread postExecutionThread;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testUseCase = new TestUseCase(threadExecutor, postExecutionThread);
    }

    @Test
    public void testUseCaseObservableReturnCorrectResult() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<Integer>();
        TestScheduler testScheduler = new TestScheduler();

        given(postExecutionThread.getScheduler()).willReturn(testScheduler);

        testUseCase.execute(testSubscriber);

        assertThat(testSubscriber.getOnNextEvents().size()).isEqualTo(0);
    }

    @Test
    public void testSubscriptionWhenExecuting() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<Integer>();
        testUseCase.execute(testSubscriber);
        testUseCase.unsubscribe();

        assertThat(testSubscriber.isUnsubscribed()).isTrue();
    }

    private static class TestUseCase extends lt.setkus.interviewtest.domain.interaction.UseCase {

        public TestUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
            super(threadExecutor, postExecutionThread);
        }

        @Override
        protected Observable buildUseCaseObservable() {
            return Observable.empty();
        }

        @Override
        public void execute(Subscriber subscriber) {
            super.execute(subscriber);
        }
    }
}
