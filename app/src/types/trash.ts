export type TrashType = "general" | "recycling" | "organic";

export interface TrashContainer {
	id: string;
	name: string;
	location: string;
	fillPercentage: number;
	lastUpdated: string;
	type: TrashType;
}

export type ContainerFilter = TrashType | "all" | "critical" | "warning" | "normal";

export type ContainerSort = "fill-desc" | "fill-asc" | "name";
