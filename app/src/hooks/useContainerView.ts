import { useState } from "react";
import type {
    ContainerFilter,
    ContainerSort,
    TrashContainer,
} from "../types/trash";

export function useContainerView(containers: TrashContainer[]) {
    const [filterType, setFilterType] =
        useState<ContainerFilter>("all");

    const [sortBy, setSortBy] =
        useState<ContainerSort>("fill-desc");

    const handleFilterChange = (value: string) => {
        setFilterType(value as ContainerFilter);
    };

    const handleSortChange = (value: string) => {
        setSortBy(value as ContainerSort);
    };

    const filtered = containers.filter((c) => {
        if (filterType === "all") return true;
        if (filterType === "critical") return c.fillPercentage >= 80;
        if (filterType === "warning")
            return c.fillPercentage >= 60 && c.fillPercentage < 80;
        if (filterType === "normal") return c.fillPercentage < 60;
        return c.type === filterType;
    });

    const sorted = [...filtered].sort((a, b) => {
        if (sortBy === "fill-desc") return b.fillPercentage - a.fillPercentage;
        if (sortBy === "fill-asc") return a.fillPercentage - b.fillPercentage;
        if (sortBy === "name") return a.name.localeCompare(b.name);
        return 0;
    });

    return {
        filterType,
        sortBy,
        handleFilterChange,
        handleSortChange,
        containers: sorted,
    };
}