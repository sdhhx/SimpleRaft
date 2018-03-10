package cc.litstar.sm;

import cc.litstar.core.ApplyMsg;
/**
 * @author hehaoxing
 * 复制状态机
 */
public interface StateMachine {
	//执行
	public void execute(ApplyMsg msg);
}
