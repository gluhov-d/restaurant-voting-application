package restaurantvoting.util.validation;

import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import restaurantvoting.HasId;
import restaurantvoting.error.DataConflictException;
import restaurantvoting.error.IllegalRequestDataException;

import java.time.LocalTime;

import static restaurantvoting.util.Util.isBetweenHalfOpen;
import static restaurantvoting.util.VotingUtil.END_OF_VOTING;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }

    public static void checkVotingTime(LocalTime votingTime) {
        if (!isBetweenHalfOpen(votingTime, null, END_OF_VOTING)) {
            throw new DataConflictException("Voting closed today at " + END_OF_VOTING);
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}