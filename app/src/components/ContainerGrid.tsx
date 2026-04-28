import { TrashContainerCard } from "./TrashContainerCard";
import type { TrashContainer } from "../types/trash";

type Props = {
    containers: TrashContainer[];
    selectedId: string | null;
};

export function ContainerGrid({ containers, selectedId }: Props) {
    if (containers.length === 0) {
        return (
            <div className="empty-state">
                <p>No containers match the selected filters</p>
            </div>
        );
    }

    return (
        <div className="containers-grid">
            {containers.map((container) => (
                <TrashContainerCard key={container.id} container={container} isSelected={selectedId === container.id} />
            ))}
        </div>
    );
}