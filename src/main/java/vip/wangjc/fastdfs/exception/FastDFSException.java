package vip.wangjc.fastdfs.exception;

/**
 * fastDFS异常
 * @author wangjc
 * @title: FastDFSException
 * @projectName wangjc-vip-fastdfs-starter
 * @date 2021/1/13 - 14:28
 */
public class FastDFSException extends Exception{

    private static final long serialVersionUID = 266607887366583188L;

    public FastDFSException() {
        super();
    }

    public FastDFSException(String message) {
        super(message);
    }

    public FastDFSException(String message, Throwable cause) {
        super(message, cause);
    }

    public FastDFSException(Throwable cause) {
        super(cause);
    }
}
