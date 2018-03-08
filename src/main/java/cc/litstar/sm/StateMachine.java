package cc.litstar.sm;

import cc.litstar.beans.ApplyMsg;
/**
 * @author hehaoxing
 * 复制状态机
 */
public interface StateMachine {
	public void execute(ApplyMsg msg);
}
