import { useEffect, useState } from "react";
import { getSubscriptions } from "../services/api";

function SubscriptionList() {
    const [subscriptions, setSubscriptions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const userId = localStorage.getItem("userId");

    useEffect(() => {
        const fetchSubscriptions = async () => {
            try {
                if (!userId || isNaN(Number(userId))) {
                    throw new Error("Invalid userId");
                }

                const data = await getSubscriptions(Number(userId));
                setSubscriptions(data);
            } catch (err) {
                setError("Failed to load subscriptions");
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchSubscriptions();
    }, [userId]);

    const formatCost = (sub) => {
        if (sub.billingCycle === "YEARLY") {
            return (sub.cost / 12).toFixed(2) + "/mo";
        }
        return Number(sub.cost).toFixed(2) + "/mo";
    };

    if (loading) {
        return <p className="text-gray-500">Loading subscriptions...</p>;
    }

    if (error) {
        return <p className="text-red-500">{error}</p>;
    }

    if (!subscriptions.length) {
        return <p className="text-gray-500">No subscriptions found</p>;
    }

    return (
        <div className="space-y-3">
            {subscriptions.map((sub) => (
                <div
                    key={sub.id}
                    className="flex justify-between items-center border-b pb-2"
                >
                    <div>
                        <p className="font-medium text-gray-800">{sub.name}</p>

                        <p className="text-sm text-gray-500">
                            {sub.category} • {sub.billingCycle} • Renew:{" "}
                            {sub.nextRenewalDate}
                        </p>
                    </div>

                    <div className="text-right">
                        <p className="font-semibold text-gray-800">
                            ${formatCost(sub)}
                        </p>

                        {sub.autoRenew && (
                            <p className="text-xs text-green-500">Auto-renew</p>
                        )}
                    </div>
                </div>
            ))}
        </div>
    );
}

export default SubscriptionList;