import type {TrashContainer} from "../types/trash"

export async function getContainers(): Promise<TrashContainer[]> {

  const response = await fetch(
    "https://smartwaste-latest.onrender.com/containers/api/v1/test"
  )

  if (!response.ok) {
    throw new Error("Failed to fetch containers")
  }

  return response.json()
}