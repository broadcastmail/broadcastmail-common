package com.broadcastmail.common.campaign.filter;

import java.util.List;

public record FilterQuery(String sql, List<Object> parameters) {}
