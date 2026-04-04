import { useState } from "react";
import { Login } from "./login/login";

import { DashboardHeader } from "./components/DashboardHeader";
import { StatsSection } from "./components/StatsSection";
import { ControlsPanel } from "./components/ControlsPanel";
import { ContainerGrid } from "./components/ContainerGrid";

import { useContainers } from "./hooks/useContainers";
import { useContainerView } from "./hooks/useContainerView";

export default function App() {
	const [isLoggedIn, setIsLoggedIn] = useState(
		localStorage.getItem("auth") === "true"
	);

	const handleLogin = () => setIsLoggedIn(true);

	if (!isLoggedIn) {
		return <Login onLogin={handleLogin} />;
	}

	const containers = useContainers(isLoggedIn);

	const {
		filterType,
		sortBy,
		handleFilterChange,
		handleSortChange,
		containers: visibleContainers,
	} = useContainerView(containers);

	return (
		<div className="dashboard-page">
			<DashboardHeader />

			<div className="dashboard-container dashboard-main">
				<StatsSection containers={containers} />

				<ControlsPanel
					filterType={filterType}
					sortBy={sortBy}
					onFilterChange={handleFilterChange}
					onSortChange={handleSortChange}
				/>

				<ContainerGrid containers={visibleContainers} />
			</div>
		</div>
	);
}