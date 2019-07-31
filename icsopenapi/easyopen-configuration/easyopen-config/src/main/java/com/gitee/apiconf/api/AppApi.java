package com.gitee.apiconf.api;

import com.gitee.apiconf.common.ChannelContext;
import com.gitee.apiconf.common.TreeNode;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@ApiService
public class AppApi {

	@Api(name = "app.list")
	@ApiDocMethod(description = "app列表")
	List<TreeNode> appRoot() {
		Set<String> appNameSet = ChannelContext.listAppNames();
		List<String> appNames = new ArrayList<>(appNameSet);
		Collections.sort(appNames);
		String state = "open";
		TreeNode rootNode = new TreeNode();
		rootNode.setId(0L);
		rootNode.setState(state);
		rootNode.setText("应用列表");
		rootNode.setRoot(true);

		List<TreeNode> children = new ArrayList<>();
		long i = 1;
		for (String appName : appNames) {
			TreeNode child = new TreeNode();
			child.setId(i++);
			child.setText(appName);
			child.setState(state);
			children.add(child);
		}

		rootNode.setChildren(children);

		return Arrays.asList(rootNode);
	}

}
