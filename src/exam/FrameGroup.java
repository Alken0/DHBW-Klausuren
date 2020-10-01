package exam;

import java.util.ArrayList;
import java.util.List;

public class FrameGroup {

	List<UpdatedFrame> frames = new ArrayList<>();

	public void register(UpdatedFrame frame) {
		this.frames.add(frame);
	}

	public void updateFrames(String c) {
		for (var f : frames) {
			f.receiveChange(c);
		}
	}
}
