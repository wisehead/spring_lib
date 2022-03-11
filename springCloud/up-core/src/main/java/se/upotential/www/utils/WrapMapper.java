package se.upotential.www.utils;

import com.github.pagehelper.Page;
//com/github/pagehelper/pagehelper/4.2.1

/**
 *
 * <p>Title:	  WrapMapper </p>
 * <p>Description 包装辅助类. </p>
 * <p>Company:    http://www.upotential.se  </p>
 * @Author        <a href="alex.chenhui@gmail.com"/>wisehead</a>
 * @CreateDate    2022年03月11日 下午13:49:11 <br/>
 * @version
 * @since         JDK 1.8
 */
public class WrapMapper {

    /**
     * Instantiates a new base mapper.
     */
    private WrapMapper() {
    }

    /**
     * Wrap.
     *
     * @param <E>
     *            the element type
     * @param code
     *            the code
     * @param message
     *            the message
     * @param o
     *            the o
     * @return the wrapper
     */
    public static <E> Wrapper<E> wrap(int code, String message, E o, Page<E> page) {
        return new Wrapper<E>(code, message, o, page);
    }

    /**
     * Wrap.
     *
     * @param <E>
     *            the element type
     * @param code
     *            the code
     * @param message
     *            the message
     * @param o
     *            the o
     * @return the wrapper
     */
    public static <E> Wrapper<E> wrap(int code, String message, E o) {
        return new Wrapper<E>(code, message, o);
    }

    /**
     * Wrap.
     *
     * @param <E>
     *            the element type
     * @param code
     *            the code
     * @param message
     *            the message
     * @return the wrapper
     */
    public static <E> Wrapper<E> wrap(int code, String message) {
        return new Wrapper<E>(code, message);
    }

    /**
     * Wrap.
     *
     * @param <E>
     *            the element type
     * @param code
     *            the code
     * @return the wrapper< e>
     */
    public static <E> Wrapper<E> wrap(int code) {
        return wrap(code, null);
    }

    /**
     * Wrap.
     *
     * @param <E>
     *            the element type
     * @param e
     *            the e
     * @return the wrapper
     */
    public static <E> Wrapper<E> wrap(Exception e) {
        return new Wrapper<E>(Wrapper.ERROR_CODE, e.getMessage());
    }

    /**
     * Un wrapper.
     *
     * @param <E>
     *            the element type
     * @param wrapper
     *            the wrapper
     * @return the e
     */
    public static <E> E unWrap(Wrapper<E> wrapper) {
        return wrapper.getResult();
    }

    /**
     * Wrap ERROR. code=100
     *
     * @param <E>
     *            the element type
     * @return the wrapper< e>
     */
    public static <E> Wrapper<E> illegalArgument() {
        return wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_, Wrapper.ILLEGAL_ARGUMENT_MESSAGE);
    }

    /**
     * Wrap ERROR. code=500
     *
     * @param <E>
     *            the element type
     * @return the wrapper< e>
     */
    public static <E> Wrapper<E> error() {
        return wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
    }

    /**
     * Wrap SUCCESS. code=200
     *
     * @param <E>
     *            the element type
     * @return the wrapper< e>
     */
    public static <E> Wrapper<E> ok() {
        return new Wrapper<E>();
    }
}
