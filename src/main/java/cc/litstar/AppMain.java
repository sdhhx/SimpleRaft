package cc.litstar;

import java.io.IOException;
import cc.litstar.core.RaftCore;

/**
 * Hello world!
 *
 */
public class AppMain {
    public static void main(String[] args) throws IOException {
        RaftCore raft = new RaftCore();
        raft.init();
        raft.start();
    }
}
