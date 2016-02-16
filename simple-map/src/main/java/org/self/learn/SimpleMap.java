package org.self.learn;

public class SimpleMap<K, V> {
	private KeyValuePair<K, V>[] keyValuePair;
	private int size;
	
	@SuppressWarnings("unchecked")
	public SimpleMap() {
		this.size = 0;
		this.keyValuePair = (KeyValuePair<K, V>[]) new KeyValuePair[5];
		
	}
	
	public int size() {
		return this.size;
	}

	public void save(K key, V value) throws DuplicateKeyFoundException {
		if(containsKey(key)) {
			throw new DuplicateKeyFoundException();
		}
		
		if(this.size<this.keyValuePair.length) {
			this.keyValuePair[this.size++] = new KeyValuePair<K, V>(key, value);
		} else {
			@SuppressWarnings("unchecked")
			KeyValuePair<K, V>[] newKeyValuePair = (KeyValuePair<K, V>[]) new KeyValuePair[this.keyValuePair.length*2];
			System.arraycopy(this.keyValuePair, 0, newKeyValuePair, 0, this.keyValuePair.length);
			this.keyValuePair = newKeyValuePair;
			this.keyValuePair[this.size++] = new KeyValuePair<K, V>(key, value);
		}
		
	}
	
	public V get(K key) throws NoSuchKeyException {
		KeyValuePair<K, V> keyValPair;
		
		if(!containsKey(key)) {
			throw new NoSuchKeyException();
		}
		
		for(int i=0;i<size;i++) {
			keyValPair = this.keyValuePair[i];
			if((key==null && keyValPair.getKey()==null) || 
					(key!=null && key.equals(keyValPair.getKey()))) {
				return keyValPair.getValue();
			}
		}
		
		return null;
	}

	public boolean containsKey(K key) {
		KeyValuePair<K, V> keyValPair;
		for(int i=0;i<size;i++) {
			keyValPair = this.keyValuePair[i];
			if((key==null && keyValPair.getKey()==null) || 
					(key!=null && key.equals(keyValPair.getKey()))) {
				return true;
			}
		}
		
		return false;
	}

	public boolean containsValue(V value) {
		KeyValuePair<K, V> keyValPair;
		for(int i=0;i<size;i++) {
			keyValPair = this.keyValuePair[i];
			if((value==null && keyValPair.getValue()==null)||
					(value!=null && value.equals(keyValPair.getValue()))) {
				return true;
			}
		}
		
		return false;
	}

	public void remove(K key) throws NoSuchKeyException {
		KeyValuePair<K, V> keyValPair;
		
		if(!containsKey(key)) {
			throw new NoSuchKeyException();
		}
		
		for(int i=0;i<size;i++) {
			keyValPair = this.keyValuePair[i];
			if((key==null && keyValPair.getKey()==null) || 
					(key!=null && key.equals(keyValPair.getKey()))) {
				if(this.size==1){
					this.keyValuePair[i] = null;
				} else {
					this.keyValuePair[i] = this.keyValuePair[this.size-1];
					this.keyValuePair[this.size-1] = null;
				}
				this.size--;
			}
		}
		
	}
	
}
