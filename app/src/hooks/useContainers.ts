import { useState, useEffect, useCallback } from "react";
import { getContainers } from "../api/ContainerApi";
import type { TrashContainer } from "../types/trash";

export function useContainers(isLoggedIn: boolean) {
    const [containers, setContainers] = useState<TrashContainer[]>([]);

    const loadContainers = useCallback(async () => {
        try {
            const apiData = await getContainers();

            const mapped: TrashContainer[] = apiData.map(
                (c: TrashContainer) => ({
                    id: c.id,
                    name: c.name,
                    location: c.location,
                    fillPercentage: c.fillPercentage,
                    lastUpdated: new Date(c.lastUpdated),
                    type: c.type,
                    latitude: c.latitude,
                    longitude: c.longitude,
                })
            );

            setContainers(mapped);
        } catch (error) {
            console.error("Failed to load containers", error);
        }
    }, []);

    useEffect(() => {
        if (!isLoggedIn) return;

        loadContainers();

        let isRunning = false;
        const interval = setInterval(async () => {
            if (isRunning) return;
            isRunning = true;
            try {
                await loadContainers();
            } finally {
                isRunning = false;
            }
        }, 10000);

        return () => clearInterval(interval);
    }, [loadContainers, isLoggedIn]);

    return containers;
}