package com.github.restaurantvoting.util.validation;

import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import com.github.restaurantvoting.HasId;
import com.github.restaurantvoting.error.IllegalRequestDataException;

import java.time.LocalTime;

import static com.github.restaurantvoting.util.Util.isBetweenHalfOpen;
import static com.github.restaurantvoting.util.VotingUtil.END_OF_VOTING;

@UtilityClass
public class ValidationUtil {
    public static final String EXCEPTION_VOTING_CLOSED = "Voting closed today at ";

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
            throw new IllegalRequestDataException(EXCEPTION_VOTING_CLOSED + END_OF_VOTING);
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}