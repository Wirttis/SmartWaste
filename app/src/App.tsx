import { useState } from "react";
import { TrashContainerCard } from "./components/TrashContainerCard";
import { StatCard } from "./components/StatCard";
import type { ContainerFilter, ContainerSort, TrashContainer } from "./types/trash";

// Mock data for trash containers
const mockContainers: TrashContainer[] = [
	{
		id: "1",
		name: "Container A-101",
		location: "Main Street & 5th Ave",
		fillPercentage: 85,
		lastUpdated: "2 hours ago",
		type: "general",
	},
	{
		id: "2",
		name: "Container A-102",
		location: "Park Plaza",
		fillPercentage: 45,
		lastUpdated: "1 hour ago",
		type: "recycling",
	},
	{
		id: "3",
		name: "Container B-201",
		location: "Downtown Center",
		fillPercentage: 92,
		lastUpdated: "30 minutes ago",
		type: "general",
	},
	{
		id: "4",
		name: "Container B-202",
		location: "City Hall",
		fillPercentage: 38,
		lastUpdated: "3 hours ago",
		type: "organic",
	},
	{
		id: "5",
		name: "Container C-301",
		location: "Riverside Park",
		fillPercentage: 67,
		lastUpdated: "1 hour ago",
		type: "recycling",
	},
	{
		id: "6",
		name: "Container C-302",
		location: "Shopping District",
		fillPercentage: 78,
		lastUpdated: "45 minutes ago",
		type: "general",
	},
	{
		id: "7",
		name: "Container D-401",
		location: "West End Station",
		fillPercentage: 23,
		lastUpdated: "2 hours ago",
		type: "general",
	},
	{
		id: "8",
		name: "Container D-402",
		location: "Community Center",
		fillPercentage: 55,
		lastUpdated: "1 hour ago",
		type: "organic",
	},
];

export default function App() {
	const [filterType, setFilterType] = useState<ContainerFilter>("all");
	const [sortBy, setSortBy] = useState<ContainerSort>("fill-desc");

	const filterValues: readonly ContainerFilter[] = [
		"all",
		"critical",
		"warning",
		"normal",
		"general",
		"recycling",
		"organic",
	];
	const sortValues: readonly ContainerSort[] = [
		"fill-desc",
		"fill-asc",
		"name",
	];

	const handleFilterChange = (value: string) => {
		if (filterValues.includes(value as ContainerFilter)) {
			setFilterType(value as ContainerFilter);
		}
	};

	const handleSortChange = (value: string) => {
		if (sortValues.includes(value as ContainerSort)) {
			setSortBy(value as ContainerSort);
		}
	};

	// Calculate statistics
	const totalContainers = mockContainers.length;
	const criticalContainers = mockContainers.filter(
		(c) => c.fillPercentage >= 80,
	).length;
	const averageFill = Math.round(
		mockContainers.reduce((sum, c) => sum + c.fillPercentage, 0) /
			totalContainers,
	);
	const needsCollection = mockContainers.filter(
		(c) => c.fillPercentage >= 60,
	).length;

	// Filter containers
	const filteredContainers = mockContainers.filter((container) => {
		if (filterType === "all") return true;
		if (filterType === "critical") return container.fillPercentage >= 80;
		if (filterType === "warning")
			return (
				container.fillPercentage >= 60 && container.fillPercentage < 80
			);
		if (filterType === "normal") return container.fillPercentage < 60;
		return container.type === filterType;
	});

	// Sort containers
	const sortedContainers = [...filteredContainers].sort((a, b) => {
		if (sortBy === "fill-desc") return b.fillPercentage - a.fillPercentage;
		if (sortBy === "fill-asc") return a.fillPercentage - b.fillPercentage;
		if (sortBy === "name") return a.name.localeCompare(b.name);
		return 0;
	});

	return (
		<div className="dashboard-page">
			{/* Header */}
			<div className="dashboard-header">
				<div className="dashboard-container dashboard-header-inner">
					<div className="dashboard-title-wrap">
						<div>
							<h1 className="dashboard-title">
								Trash Container Tracker
							</h1>
							<p className="dashboard-subtitle">
								Real-time monitoring and management system
							</p>
						</div>
					</div>
				</div>
			</div>

			<div className="dashboard-container dashboard-main">
				{/* Statistics Cards */}
				<div className="stats-grid">
					<StatCard
						title="Total Containers"
						value={totalContainers}
					/>
					<StatCard
						title="Critical Level"
						value={criticalContainers}
						subtitle="≥ 80% full"
					/>
					<StatCard
						title="Average Fill"
						value={`${averageFill}%`}
					/>
					<StatCard
						title="Needs Collection"
						value={needsCollection}
						subtitle="≥ 60% full"
					/>
				</div>

				{/* Filters and Controls */}
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
									onClick={() => handleFilterChange(tab.value)}
								>
									{tab.label}
								</button>
							))}
						</div>

						<div className="control-selects">
							<select
								className="control-select"
								value={filterType}
								onChange={(event) => handleFilterChange(event.target.value)}
							>
								<option value="all">All Types</option>
								<option value="general">General</option>
								<option value="recycling">Recycling</option>
								<option value="organic">Organic</option>
							</select>

							<select
								className="control-select"
								value={sortBy}
								onChange={(event) => handleSortChange(event.target.value)}
							>
								<option value="fill-desc">Fill % (High to Low)</option>
								<option value="fill-asc">Fill % (Low to High)</option>
								<option value="name">Name (A-Z)</option>
							</select>
						</div>
					</div>
				</div>

				{/* Container Grid */}
				<div className="containers-grid">
					{sortedContainers.map((container) => (
						<TrashContainerCard
							key={container.id}
							container={container}
						/>
					))}
				</div>

				{sortedContainers.length === 0 && (
					<div className="empty-state">
						<p>
							No containers match the selected filters
						</p>
					</div>
				)}
			</div>
		</div>
	);
}
