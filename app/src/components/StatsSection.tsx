import { StatCard } from "./StatCard";
import type { TrashContainer } from "../types/trash";

type Props = {
    containers: TrashContainer[];
};

export function StatsSection({ containers }: Props) {
    const totalContainers = containers.length;

    const criticalContainers = containers.filter(
        (c) => c.fillPercentage >= 80
    ).length;

    const averageFill = Math.round(
        containers.reduce((sum, c) => sum + c.fillPercentage, 0) /
        (totalContainers || 1)
    );

    const needsCollection = containers.filter(
        (c) => c.fillPercentage >= 60
    ).length;

    return (
        <div className="stats-grid">
            <StatCard title="Critical Level" value={criticalContainers} subtitle="≥ 80% full" />
            <StatCard title="Needs Collection" value={needsCollection} subtitle="≥ 60% full" />
            <StatCard title="Average Fill" value={`${averageFill}%`} />
            <StatCard title="Total Containers" value={totalContainers} />
        </div>
    );
}