package org.eclipse.rap.rwt.commandbar;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rwt.lifecycle.WidgetUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class CmdBar {

	private final Composite commandBar;

	private List<CmdBarGroup> groups;
	
	private boolean isWithinHandleResize = false;
	
	public CmdBar(final Composite parent) {
		commandBar = new Composite(parent, SWT.NONE);
		commandBar.setData(WidgetUtil.CUSTOM_VARIANT, "cmdBar");
		commandBar.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(final ControlEvent e) {
				handleResize();
			}
		});
		commandBar.setLayout(new GridLayout(1, false));
	}
	
	/**
	 * Check if there's enough space for all groups. If not, display them
	 * in compact mode.
	 */
	protected void handleResize() {
		if (!isWithinHandleResize) {
			isWithinHandleResize = true;
			
			try {
				final int cmdBarWidth = commandBar.getSize().x;
				final int groupSpacing = ((GridLayout)commandBar.getLayout()).horizontalSpacing;
				int groupWidth = 0;
				final int[] groupWidths = new int[getGroups().size()];
				for (int i=0; i<getGroups().size(); i++) {
					final CmdBarGroup group = getGroups().get(i);
					final int groupX = group.getCurrentWidth();
					groupWidths[i] = groupX;
					groupWidth += groupX + groupSpacing;
				}
				
				// Start compacting groups, starting with the last one
				for (int i = getGroups().size() - 1; i >= 0; i--) {
					CmdBarGroup group = getGroups().get(i);
					
					if (!group.isCompact() && groupWidth > cmdBarWidth) {
						group.makeCompact();
						final int oldWidth = groupWidths[i];
						final int compactWidth = group.getGrpContainer().computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
						log("compact " + group + " old: " + oldWidth + " new: " + compactWidth);
						groupWidth -= oldWidth;
						groupWidth += compactWidth;
					} else if (group.isCompact()){
						// TODO: This doesn't work if Group's content changes
						int widthDelta = group.getUncompactedWidth() - group.getCurrentWidth();
						if (groupWidth + widthDelta < cmdBarWidth) {
							group.unmakeCompact();
							groupWidth += widthDelta;
							log("uncompact " + group);
						}
					}
					
				}
				commandBar.layout();

			} finally {
				isWithinHandleResize = false;
			}
		}
	}

	private static void log(final String msg) {
		System.out.println(msg);
	}

	public Control getControl() {
		return commandBar;
	}

	Composite getContainer() {
		return commandBar;
	}

	void addNewGroup(final CmdBarGroup group) {
		getGroups().add(group);
		
		if (commandBar.getLayout() == null) {
			commandBar.setLayout(new GridLayout(1, false));
		} else {
			final GridLayout layout = (GridLayout) commandBar.getLayout();
			layout.numColumns++;
		}
		commandBar.layout();
	}

	void removeGroup(final CmdBarGroup group) {
		getGroups().remove(group);
		
		if (commandBar.getLayout() != null) {
			final GridLayout layout = (GridLayout) commandBar.getLayout();
			layout.numColumns--;
		}
		commandBar.layout();
		
	}
	
	
	protected List<CmdBarGroup> getGroups() {
		if (groups == null) {
			groups = new ArrayList<CmdBarGroup>();
		}
		return groups;
	}
	
}
