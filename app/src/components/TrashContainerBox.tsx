
interface TrashContainer {
  id: string;
  location: string;
  name: string;
  fill_percentage: number;
  last_updated: string;
}
interface TrashContainerBoxProps {
    container:TrashContainer
}
export function TrashContainerBox({container} : TrashContainerBoxProps) {
    return (
        <>
        <div>
            {container.name}
            , {container.fill_percentage}%
            {} {container.last_updated}
        </div>
        </>
    )
}