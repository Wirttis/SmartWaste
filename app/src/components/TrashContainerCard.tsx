import type { TrashContainer } from "../types/trash";

interface TrashContainerCardProps {
  container: TrashContainer;
}

export function TrashContainerCard({ container }: TrashContainerCardProps) {
  const getStatusColor = (fillPercentage: number) => {
    if (fillPercentage >= 80) return "critical";
    if (fillPercentage >= 60) return "warning";
    return "normal";
  };

  const getStatusText = (fillPercentage: number) => {
    if (fillPercentage >= 80) return "Critical";
    if (fillPercentage >= 60) return "Needs Attention";
    return "Normal";
  };

  const getProgressColor = (fillPercentage: number) => {
    if (fillPercentage >= 80) return "bg-red-500";
    if (fillPercentage >= 60) return "bg-yellow-500";
    return "bg-green-500";
  };

  const getTypeIcon = () => {
    switch (container.type) {
      case "recycling":
        return "♻️";
      case "organic":
        return "🌱";
      default:
        return "";
    }
  };

  const typeIcon = getTypeIcon();

  return (
    <div className="panel container-card">
      <div className="container-top-row">
        <div>
          <h3 className="container-title">
            {container.name}
            {typeIcon && <span className="container-type-icon">{typeIcon}</span>}
          </h3>
          <p className="container-location">{container.location}</p>
        </div>
        <span className={`status-badge ${getStatusColor(container.fillPercentage)}`}>
          {getStatusText(container.fillPercentage)}
        </span>
      </div>

      <div>
        <div className="fill-row">
          <span className="fill-label">Fill Level</span>
          <span className="fill-value">{container.fillPercentage}%</span>
        </div>
        <div className="progress-track">
          <div
            className={`progress-fill ${getProgressColor(container.fillPercentage)}`}
            style={{ width: `${container.fillPercentage}%` }}
          />
        </div>
      </div>

      <div className="container-footer">
        Last updated: {container.lastUpdated}
      </div>
    </div>
  );
}
