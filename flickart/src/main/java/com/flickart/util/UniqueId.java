package com.flickart.util;

import java.util.UUID;

public class UniqueId {
	public static String getUniqueId() {
		return UUID.randomUUID().toString().substring(0, 20);
	}
}
