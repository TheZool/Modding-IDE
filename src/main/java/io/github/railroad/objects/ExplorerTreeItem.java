package io.github.railroad.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ExplorerTreeItem extends TreeItem<File> {
	private boolean childrenCached = false;

	public ExplorerTreeItem(File folder) {
		super(folder);
	}

	@Override
	public ObservableList<TreeItem<File>> getChildren() {
		if(!childrenCached) {
			super.getChildren().setAll(evaluateChildren(this));
			childrenCached = true;
		}
		return super.getChildren();
	}

	@Override
	public boolean isLeaf() {
		return getValue().isFile();
	}

	private ObservableList<TreeItem<File>> evaluateChildren(TreeItem<File> rootItem) {
		File root = rootItem.getValue();
		if(root == null || root.isFile()) {
			return FXCollections.emptyObservableList();
		}
		File[] files = root.listFiles();
		if(files != null) {
			return FXCollections.observableList(
				Arrays.stream(files).map(ExplorerTreeItem::new).collect(Collectors.toList())
			);
		}
		return FXCollections.emptyObservableList();
	}

}
