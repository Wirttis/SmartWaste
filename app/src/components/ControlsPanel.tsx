import type { ContainerFilter, ContainerSort } from "../types/trash";

type Props = {
    filterType: ContainerFilter;
    sortBy: ContainerSort;
    onFilterChange: (value: string) => void;
    onSortChange: (value: string) => void;
};

export function ControlsPanel({
                                  filterType,
                                  sortBy,
                                  onFilterChange,
                                  onSortChange,
                              }: Props) {
    return (
        <div className="panel controls-panel">
            <div className="controls-row">
                <div className="filter-tabs" role="tablist" aria-label="Fill level filters">
                    {[
                        { value: "all", label: "All" },
                        { value: "critical", label: "Critical" },
                        { value: "warning", label: "Warning" },
                        { value: "normal", label: "Normal" },
                    ].map((tab) => (
                        <button
                            key={tab.value}
                            type="button"
                            className={`filter-tab ${filterType === tab.value ? "active" : ""}`}
                            onClick={() => onFilterChange(tab.value)}
                        >
                            {tab.label}
                        </button>
                    ))}
                </div>

                <div className="control-selects">
                    <select
                        className="control-select"
                        value={filterType}
                        onChange={(e) => onFilterChange(e.target.value)}
                    >
                        <option value="all">All Types</option>
                        <option value="general">General</option>
                        <option value="recycling">Recycling</option>
                        <option value="organic">Organic</option>
                    </select>

                    <select
                        className="control-select"
                        value={sortBy}
                        onChange={(e) => onSortChange(e.target.value)}
                    >
                        <option value="fill-desc">Fill % (High to Low)</option>
                        <option value="fill-asc">Fill % (Low to High)</option>
                        <option value="name">Name (A-Z)</option>
                    </select>
                </div>
            </div>
        </div>
    );
}