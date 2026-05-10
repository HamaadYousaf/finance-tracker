import { useEffect, useState } from "react";
import { getDashboard } from "../services/api";

function Dashboard() {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const userId = localStorage.getItem("userId");

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await getDashboard(userId);
                setData(res);
            } catch (err) {
                setError("Failed to load dashboard");
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [userId]);

    if (loading) {
        return <p className="text-gray-500">Loading dashboard...</p>;
    }

    if (error) {
        return <p className="text-red-500">{error}</p>;
    }

    if (!data) return null;

    const {
        totalExpensesThisMonth,
        totalMonthlySubscriptionCost,
        upcomingRenewals,
        categoryBreakdown,
    } = data;

    return (
        <div className="space-y-6">

            {/* Top Cards */}
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">

                <div className="bg-white p-4 rounded-xl shadow">
                    <h3 className="text-sm text-gray-500">Monthly Expenses</h3>
                    <p className="text-2xl font-bold text-gray-800">
                        ${Number(totalExpensesThisMonth).toFixed(2)}
                    </p>
                </div>

                <div className="bg-white p-4 rounded-xl shadow">
                    <h3 className="text-sm text-gray-500">Subscription Cost</h3>
                    <p className="text-2xl font-bold text-gray-800">
                        ${Number(totalMonthlySubscriptionCost).toFixed(2)}
                    </p>
                </div>

                <div className="bg-white p-4 rounded-xl shadow">
                    <h3 className="text-sm text-gray-500">Upcoming Renewals</h3>
                    <p className="text-2xl font-bold text-gray-800">
                        {upcomingRenewals.length}
                    </p>
                </div>

            </div>

            {/* Category Breakdown */}
            <div className="bg-white p-4 rounded-xl shadow">
                <h3 className="text-lg font-semibold mb-4">
                    Category Breakdown
                </h3>

                {Object.keys(categoryBreakdown).length === 0 ? (
                    <p className="text-gray-500">No data available</p>
                ) : (
                    <ul className="space-y-2">
                        {Object.entries(categoryBreakdown).map(([category, amount]) => (
                            <li
                                key={category}
                                className="flex justify-between border-b pb-1"
                            >
                                <span className="text-gray-600">{category}</span>
                                <span className="font-medium">
                                    ${Number(amount).toFixed(2)}
                                </span>
                            </li>
                        ))}
                    </ul>
                )}
            </div>

        </div>
    );
}

export default Dashboard;