import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import type  { TrashContainer } from "../types/trash";
import "leaflet/dist/leaflet.css";
import "./map.css";

type Props = {
    containers: TrashContainer[];
    selectedId: string | null;
    onSelect: (id: string | null) => void;
};

export function Map({ containers, selectedId, onSelect }: Props) {

    return (
        <div style={{ height: "400px", width: "100%" }}>
            <MapContainer
                //@ts-ignore
                center =  {[62.42080283615617, 28.64979250504363]}
                zoom={6}
                scrollWheelZoom={true}
                style={{ height: "100%", width: "100%" }}
            >
                <TileLayer
                    //@ts-ignore
                    attribution='&copy; OpenStreetMap contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />

                {containers.map((container) => (
                    <Marker
                        
                        key={container.id}

                        
                        position={[container.latitude, container.longitude]}
                        eventHandlers={{
                            click: () => onSelect(selectedId === container.id ? null : container.id),
                        }}
                    >
                        <Popup>
                            <div style={{ width: 250 }}>
                                {container.name}
                            </div>
                        </Popup>
                    </Marker>
                ))}

            </MapContainer>
        </div>
    );
}