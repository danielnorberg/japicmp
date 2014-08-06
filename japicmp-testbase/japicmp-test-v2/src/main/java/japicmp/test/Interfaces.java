package japicmp.test;

public class Interfaces {
	
	public interface TestInterface {
		
	}
	
	public interface SecondTestInterface {
		
	}
	
	public static class InterfaceToNoInterfaceClass {
		
	}
	
	public static class NoInterfaceToInterfaceClass implements TestInterface {
		
	}
	
	public static class NoInterfaceRemainsNoInterfaceClass {
		
	}
	
	public static class InterfaceRemainsInterfaceClass implements TestInterface {
		
	}
	
	public static class InterfaceChangesClass implements SecondTestInterface {
		
	}
}