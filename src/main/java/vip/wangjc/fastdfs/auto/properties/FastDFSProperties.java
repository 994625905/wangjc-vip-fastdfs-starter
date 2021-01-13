package vip.wangjc.fastdfs.auto.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 上传配置文件
 * @author wangjc
 * @title: FastdfsProperties
 * @projectName wangjc-vip-fastdfs-starter
 * @date 2021/1/13 - 14:11
 */
@ConfigurationProperties(prefix = "fdfs.define")
public class FastDFSProperties {

    /**
     * 访问地址
     */
    private String requestHost;

    /**
     * 端口
     */
    private String storagePort;

    /**
     * 最大上限
     */
    private Long maxSize;

    public String getRequestHost() {
        return requestHost;
    }

    public void setRequestHost(String requestHost) {
        this.requestHost = requestHost;
    }

    public String getStoragePort() {
        return storagePort;
    }

    public void setStoragePort(String storagePort) {
        this.storagePort = storagePort;
    }

    public Long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Long maxSize) {
        this.maxSize = maxSize;
    }
}
