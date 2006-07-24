package bl0rg.vj.reflection;

public class ParameterSetting {
	String name;
	Object value;
	
	public ParameterSetting(String name, Object value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setParameter(Object obj) {
		MidiAppReflection.setParameter(obj, name, value);
	}
	
}
