package vip.wangjc.fastdfs.auto.configure;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import vip.wangjc.fastdfs.auto.properties.FastDFSProperties;
import vip.wangjc.fastdfs.service.FastDFSService;

/**
 * 自动化配置
 * @author wangjc
 * @title: FastDFSAutoConfigure
 * @projectName wangjc-vip-fastdfs-starter
 * @date 2021/1/13 - 14:53
 */
@Configuration
@EnableConfigurationProperties({FastDFSProperties.class})
@EnableMBeanExport(registration= RegistrationPolicy.IGNORE_EXISTING)
@Import(FdfsClientConfig.class)
public class FastDFSAutoConfigure {

    private final FastDFSProperties fastDFSProperties;

    public FastDFSAutoConfigure(FastDFSProperties fastDFSProperties){
        this.fastDFSProperties = fastDFSProperties;
    }

    /**
     * 注册fastDFSService
     * @param fastFileStorageClient
     * @return
     */
    @Bean
    @ConditionalOnBean(FastFileStorageClient.class)
    public FastDFSService fastDFSService(FastFileStorageClient fastFileStorageClient){
        return new FastDFSService(fastFileStorageClient, this.fastDFSProperties);
    }

}
