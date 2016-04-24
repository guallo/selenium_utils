package selenium_utils;

import java.util.concurrent.locks.ReentrantLock;

public final class None {
	private static None _instance = null;
	private static ReentrantLock _lock = new ReentrantLock();
	
	private None() { }
	
	public static None getInstance() {
		_lock.lock();
		
		try {
			if (None._instance == null) {
				None._instance = new None();
			}
		} finally {
			_lock.unlock();
		}
		
		return None._instance;
	}
}
