package icurriculum.global.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import jakarta.annotation.PreDestroy;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.Socket;

@Slf4j
@Configuration
@Setter
public class SSHConfig {



    @Value("${ec2.remote_jump_host}")
    private String sshHost;

    @Value("${ec2.ssh_port}")
    private int sshPort;

    @Value("${ec2.user}")
    private String sshUser;

    @Value("${ec2.private_key_path}")
    private String privateKeyPath;

    @Value("${ec2.database_endpoint}")
    private String remoteHost;

    @Value("${ec2.database_port}")
    private int remotePort;


    private Session session;

    @PreDestroy
    public void destroy() {
        if (session.isConnected()) {
            session.disconnect();
        }
    }


    public Integer buildSshConnection() {
        Integer forwardPort = null;
        try {

            JSch jsch = new JSch();
            jsch.addIdentity(privateKeyPath);
            session = jsch.getSession(sshUser, sshHost, sshPort);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            // SSH 터널 설정
            forwardPort = session.setPortForwardingL(0, remoteHost, remotePort);



        } catch (Exception e) {
            System.err.println("SSH 터널 연결 실패: " + e.getMessage());
            this.destroy();
            throw new RuntimeException(e);
        }
        return forwardPort;
    }

    private boolean isLocalPortOpen(int port) {
        try (Socket socket = new Socket("localhost", port)) {
            return true;  // 연결 성공, 포트가 열려 있음
        } catch (IOException e) {
            return false; // 연결 실패, 포트가 열려 있지 않음
        }
    }
}
