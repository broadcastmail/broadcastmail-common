package com.broadcastmail.common.campaign.filter;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CampaignFilterSerializer {

    private static final Set<String> JSON_METADATA_COLUMNS = Set.of("raw_user_meta_data", "raw_app_meta_data");
    public FilterQuery serialize(List<CampaignFilter> filters) {
        if (filters == null || filters.isEmpty()) {
            return new FilterQuery("", List.of());
        }

        String fragments = filters.stream().sorted(Comparator.comparingInt(CampaignFilter::getFilterOrder))
                .map(this::buildFragment)
                .collect(Collectors.joining(" AND "));
        String sql = "WHERE " + fragments;

        List<Object> parameters = filters.stream()
                .sorted(Comparator.comparingInt(CampaignFilter::getFilterOrder))
                .map(f -> f.getOperator() == FilterOperator.CONTAINS
                        ? "%" + f.getFilterValue() + "%"
                        : f.getFilterValue())
                .collect(Collectors.toList());
        return new FilterQuery(sql, parameters);
    }

    private String buildFragment(CampaignFilter filter) {
        if (!filter.getColumnName().matches("[a-zA-Z_]\\w*")) {
            throw new IllegalArgumentException("Invalid column name: " + filter.getColumnName());
        }
        String operator = switch (filter.getOperator()) {
            case EQ -> "=";
            case NEQ -> "!=";
            case GT -> ">";
            case LT -> "<";
            case CONTAINS -> "ILIKE";
        };
        return switch (filter.getSource()) {
            case PROFILE_TABLE, AUTH_METADATA -> "\"" + filter.getColumnName() + "\" " + operator + " ?";
            case AUTH_METADATA_JSON -> {
                if (!JSON_METADATA_COLUMNS.contains(filter.getColumnName())) {
                    throw new IllegalArgumentException("Invalid metadata column: " + filter.getColumnName());
                }
                if (filter.getJsonKey() == null || !filter.getJsonKey().matches("[a-zA-Z_]\\w*")) {
                    throw new IllegalArgumentException("Invalid metadata key: " + filter.getJsonKey());
                }
                yield "\"" + filter.getColumnName() + "\"->>'" + filter.getJsonKey() + "' " + operator + " ?";
            }
        };
    }
}
