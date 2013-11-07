package aut.htlinn.ortner.spe;

public class VirtualFile {

	private String absolutePath;
	private String name;
	private long size;
	private String lastModified;
	private boolean isDir;
	
	public VirtualFile(String absolutePath, String name, long size,
			String lastModified, boolean isDir) {
		super();
		this.absolutePath = absolutePath;
		this.name = name;
		this.size = size;
		this.lastModified = lastModified;
		this.isDir = isDir;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	public String getLastModified() {
		return lastModified;
	}

	public boolean isDir() {
		return isDir;
	}
	
	
	
}
