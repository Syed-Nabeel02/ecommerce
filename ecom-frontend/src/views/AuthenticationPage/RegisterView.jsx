/**
 * RegisterView.jsx
 * User registration page with optional address and payment card sections.
 * Supports collapsible sections for default address and payment card.
 * Uses react-hook-form for validation.
 */

import React, { useState } from 'react'
import { useForm } from 'react-hook-form';
import { MdPersonAdd, MdLocationOn, MdCreditCard } from 'react-icons/md';
import { Link, useNavigate } from 'react-router-dom';
import TextInput from '../../components/ui/forms/TextInput';
import { useDispatch } from 'react-redux';
import { registerNewUser } from '../../store/actions';
import toast from 'react-hot-toast';
import CollapsiblePanel from '../../components/ui/forms/CollapsiblePanel';

const RegisterView = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [isLoading, setIsLoading] = useState(false);
    const [showAddressSection, setShowAddressSection] = useState(false);
    const [showPaymentSection, setShowPaymentSection] = useState(false);

    const {
        register,
        handleSubmit,
        reset,
        formState: {errors},
    } = useForm({
        mode: "onTouched",
    });

    const handleRegister = async (formData) => {
        dispatch(registerNewUser(formData, toast, reset, navigate, setIsLoading));
    };

    return (
        <div className="min-h-[calc(100vh-64px)] bg-gradient-to-br from-slate-50 to-slate-100 flex justify-center items-center p-4">
            <div className="w-full max-w-2xl">
                {/* Card container */}
                <div className="bg-white shadow-lg rounded-lg overflow-hidden">
                    {/* Header section */}
                    <div className="bg-gradient-to-r from-red-600 to-red-700 px-6 sm:px-8 py-10 flex flex-col items-center justify-center space-y-3">

                        <h1 className="text-white text-center font-montserrat text-2xl sm:text-3xl font-bold">
                            Join Us Today
                        </h1>
                        <p className="text-red-100 text-sm text-center">
                            Create your account to get started
                        </p>
                    </div>

                    {/* Form section */}
                    <form
                        onSubmit={handleSubmit(handleRegister)}
                        className="px-6 sm:px-8 py-8 space-y-6">

                        {/* Required fields */}
                        <div className="space-y-4">
                            <h2 className="text-sm font-semibold text-slate-700 flex items-center gap-2">
                                <span className="w-1 h-1 bg-red-600 rounded-full"></span>
                                Basic Information
                            </h2>
                            <div className="flex flex-col gap-4">
                                <div>
                                    <label className="block text-sm font-semibold text-slate-700 mb-2">
                                        Username
                                    </label>
                                    <input
                                        type="text"
                                        placeholder="Choose a username"
                                        disabled={isLoading}
                                        className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                            errors.username
                                                ? "border-red-400 focus:border-red-500 bg-red-50"
                                                : "border-slate-200 focus:border-red-600 bg-slate-50"
                                        } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                        {...register("username", {
                                            required: "Please provide a username",
                                            minLength: {
                                                value: 3,
                                                message: "Username should have at least 3 characters",
                                            },
                                        })}
                                    />
                                    {errors.username && (
                                        <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                            <span>•</span> {errors.username.message}
                                        </p>
                                    )}
                                </div>

                                <div>
                                    <label className="block text-sm font-semibold text-slate-700 mb-2">
                                        Email
                                    </label>
                                    <input
                                        type="email"
                                        placeholder="Provide your email address"
                                        disabled={isLoading}
                                        className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                            errors.email
                                                ? "border-red-400 focus:border-red-500 bg-red-50"
                                                : "border-slate-200 focus:border-red-600 bg-slate-50"
                                        } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                        {...register("email", {
                                            required: "Please provide your email",
                                            pattern: {
                                                value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                                                message: "Email format is not valid",
                                            },
                                        })}
                                    />
                                    {errors.email && (
                                        <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                            <span>•</span> {errors.email.message}
                                        </p>
                                    )}
                                </div>

                                <div>
                                    <label className="block text-sm font-semibold text-slate-700 mb-2">
                                        Password
                                    </label>
                                    <input
                                        type="password"
                                        placeholder="Create a password (minimum 6 characters)"
                                        disabled={isLoading}
                                        className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                            errors.password
                                                ? "border-red-400 focus:border-red-500 bg-red-50"
                                                : "border-slate-200 focus:border-red-600 bg-slate-50"
                                        } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                        {...register("password", {
                                            required: "Please create a password",
                                            minLength: {
                                                value: 6,
                                                message: "Password should have at least 6 characters",
                                            },
                                        })}
                                    />
                                    {errors.password && (
                                        <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                            <span>•</span> {errors.password.message}
                                        </p>
                                    )}
                                </div>
                            </div>
                        </div>

                        {/* Optional Address Section */}
                        <div className="border-t pt-6">
                            <button
                                type="button"
                                onClick={() => setShowAddressSection(!showAddressSection)}
                                className="w-full flex items-center justify-between p-3 bg-slate-50 hover:bg-slate-100 rounded-lg transition-colors">
                                <div className="flex items-center gap-3">
                                    <MdLocationOn className="text-red-600 text-lg" />
                                    <span className="text-sm font-semibold text-slate-700">Add Default Address</span>
                                </div>
                                <span className={`text-slate-600 transition-transform ${showAddressSection ? "rotate-180" : ""}`}>
                                    ▼
                                </span>
                            </button>
                            {showAddressSection && (
                                <div className="mt-4 space-y-4 p-4 bg-slate-50 rounded-lg">
                                    <div>
                                        <label className="block text-sm font-semibold text-slate-700 mb-2">
                                            Building Name
                                        </label>
                                        <input
                                            type="text"
                                            placeholder="Provide building name"
                                            disabled={isLoading}
                                            className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                                errors.buildingName
                                                    ? "border-red-400 focus:border-red-500 bg-red-50"
                                                    : "border-slate-200 focus:border-red-600 bg-white"
                                            } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                            {...register("buildingName", {
                                                required: showAddressSection && "Please provide building name",
                                                minLength: showAddressSection && {
                                                    value: 5,
                                                    message: "Building name should have at least 5 characters",
                                                },
                                            })}
                                        />
                                        {errors.buildingName && (
                                            <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                                <span>•</span> {errors.buildingName.message}
                                            </p>
                                        )}
                                    </div>

                                    <div>
                                        <label className="block text-sm font-semibold text-slate-700 mb-2">
                                            Street
                                        </label>
                                        <input
                                            type="text"
                                            placeholder="Provide street address"
                                            disabled={isLoading}
                                            className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                                errors.street
                                                    ? "border-red-400 focus:border-red-500 bg-red-50"
                                                    : "border-slate-200 focus:border-red-600 bg-white"
                                            } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                            {...register("street", {
                                                required: showAddressSection && "Please provide street address",
                                                minLength: showAddressSection && {
                                                    value: 5,
                                                    message: "Street address should have at least 5 characters",
                                                },
                                            })}
                                        />
                                        {errors.street && (
                                            <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                                <span>•</span> {errors.street.message}
                                            </p>
                                        )}
                                    </div>

                                    <div className="grid grid-cols-2 gap-4">
                                        <div>
                                            <label className="block text-sm font-semibold text-slate-700 mb-2">
                                                City
                                            </label>
                                            <input
                                                type="text"
                                                placeholder="Provide city name"
                                                disabled={isLoading}
                                                className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                                    errors.city
                                                        ? "border-red-400 focus:border-red-500 bg-red-50"
                                                        : "border-slate-200 focus:border-red-600 bg-white"
                                                } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                                {...register("city", {
                                                    required: showAddressSection && "Please provide city name",
                                                    minLength: showAddressSection && {
                                                        value: 4,
                                                        message: "City name should have at least 4 characters",
                                                    },
                                                })}
                                            />
                                            {errors.city && (
                                                <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                                    <span>•</span> {errors.city.message}
                                                </p>
                                            )}
                                        </div>
                                        <div>
                                            <label className="block text-sm font-semibold text-slate-700 mb-2">
                                                State
                                            </label>
                                            <input
                                                type="text"
                                                placeholder="Provide state or province"
                                                disabled={isLoading}
                                                className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                                    errors.state
                                                        ? "border-red-400 focus:border-red-500 bg-red-50"
                                                        : "border-slate-200 focus:border-red-600 bg-white"
                                                } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                                {...register("state", {
                                                    required: showAddressSection && "Please provide state or province",
                                                    minLength: showAddressSection && {
                                                        value: 2,
                                                        message: "State should have at least 2 characters",
                                                    },
                                                })}
                                            />
                                            {errors.state && (
                                                <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                                    <span>•</span> {errors.state.message}
                                                </p>
                                            )}
                                        </div>
                                    </div>

                                    <div className="grid grid-cols-2 gap-4">
                                        <div>
                                            <label className="block text-sm font-semibold text-slate-700 mb-2">
                                                Country
                                            </label>
                                            <input
                                                type="text"
                                                placeholder="Provide country name"
                                                disabled={isLoading}
                                                className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                                    errors.country
                                                        ? "border-red-400 focus:border-red-500 bg-red-50"
                                                        : "border-slate-200 focus:border-red-600 bg-white"
                                                } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                                {...register("country", {
                                                    required: showAddressSection && "Please provide country name",
                                                    minLength: showAddressSection && {
                                                        value: 2,
                                                        message: "Country name should have at least 2 characters",
                                                    },
                                                })}
                                            />
                                            {errors.country && (
                                                <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                                    <span>•</span> {errors.country.message}
                                                </p>
                                            )}
                                        </div>
                                        <div>
                                            <label className="block text-sm font-semibold text-slate-700 mb-2">
                                                Postal Code
                                            </label>
                                            <input
                                                type="text"
                                                placeholder="Provide postal or ZIP code"
                                                disabled={isLoading}
                                                className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                                    errors.pincode
                                                        ? "border-red-400 focus:border-red-500 bg-red-50"
                                                        : "border-slate-200 focus:border-red-600 bg-white"
                                                } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                                {...register("pincode", {
                                                    required: showAddressSection && "Please provide postal or ZIP code",
                                                    minLength: showAddressSection && {
                                                        value: 4,
                                                        message: "Postal code should have at least 4 characters",
                                                    },
                                                })}
                                            />
                                            {errors.pincode && (
                                                <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                                    <span>•</span> {errors.pincode.message}
                                                </p>
                                            )}
                                        </div>
                                    </div>
                                </div>
                            )}
                        </div>

                        {/* Optional Payment Card Section */}
                        <div className="border-t pt-6">
                            <button
                                type="button"
                                onClick={() => setShowPaymentSection(!showPaymentSection)}
                                className="w-full flex items-center justify-between p-3 bg-slate-50 hover:bg-slate-100 rounded-lg transition-colors">
                                <div className="flex items-center gap-3">
                                    <MdCreditCard className="text-red-600 text-lg" />
                                    <span className="text-sm font-semibold text-slate-700">Add Default Payment Card</span>
                                </div>
                                <span className={`text-slate-600 transition-transform ${showPaymentSection ? "rotate-180" : ""}`}>
                                    ▼
                                </span>
                            </button>
                            {showPaymentSection && (
                                <div className="mt-4 space-y-4 p-4 bg-slate-50 rounded-lg">
                                    <div>
                                        <label className="block text-sm font-semibold text-slate-700 mb-2">
                                            Card Number
                                        </label>
                                        <input
                                            type="text"
                                            placeholder="Provide card number (13-19 digits)"
                                            disabled={isLoading}
                                            className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                                errors.cardNumber
                                                    ? "border-red-400 focus:border-red-500 bg-red-50"
                                                    : "border-slate-200 focus:border-red-600 bg-white"
                                            } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                            {...register("cardNumber", {
                                                required: showPaymentSection && "Please provide card number",
                                                pattern: showPaymentSection && {
                                                    value: /^[0-9]{13,19}$/,
                                                    message: "Card number should be 13-19 digits",
                                                },
                                            })}
                                        />
                                        {errors.cardNumber && (
                                            <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                                <span>•</span> {errors.cardNumber.message}
                                            </p>
                                        )}
                                    </div>

                                    <div>
                                        <label className="block text-sm font-semibold text-slate-700 mb-2">
                                            Cardholder Name
                                        </label>
                                        <input
                                            type="text"
                                            placeholder="Cardholder's name"
                                            disabled={isLoading}
                                            className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                                errors.cardholderName
                                                    ? "border-red-400 focus:border-red-500 bg-red-50"
                                                    : "border-slate-200 focus:border-red-600 bg-white"
                                            } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                            {...register("cardholderName", {
                                                required: showPaymentSection && "Please provide cardholder name",
                                                minLength: showPaymentSection && {
                                                    value: 2,
                                                    message: "Cardholder name should have at least 2 characters",
                                                },
                                            })}
                                        />
                                        {errors.cardholderName && (
                                            <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                                <span>•</span> {errors.cardholderName.message}
                                            </p>
                                        )}
                                    </div>

                                    <div className="grid grid-cols-3 gap-4">
                                        <div>
                                            <label className="block text-sm font-semibold text-slate-700 mb-2">
                                                Expiry Month
                                            </label>
                                            <input
                                                type="number"
                                                placeholder="MM"
                                                disabled={isLoading}
                                                className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                                    errors.expiryMonth
                                                        ? "border-red-400 focus:border-red-500 bg-red-50"
                                                        : "border-slate-200 focus:border-red-600 bg-white"
                                                } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                                {...register("expiryMonth", {
                                                    required: showPaymentSection && "Please provide expiry month",
                                                    min: showPaymentSection && {
                                                        value: 1,
                                                        message: "Month should be between 1-12",
                                                    },
                                                    max: showPaymentSection && {
                                                        value: 12,
                                                        message: "Month should be between 1-12",
                                                    },
                                                })}
                                            />
                                            {errors.expiryMonth && (
                                                <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                                    <span>•</span> {errors.expiryMonth.message}
                                                </p>
                                            )}
                                        </div>
                                        <div>
                                            <label className="block text-sm font-semibold text-slate-700 mb-2">
                                                Expiry Year
                                            </label>
                                            <input
                                                type="number"
                                                placeholder="YYYY"
                                                disabled={isLoading}
                                                className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                                    errors.expiryYear
                                                        ? "border-red-400 focus:border-red-500 bg-red-50"
                                                        : "border-slate-200 focus:border-red-600 bg-white"
                                                } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                                {...register("expiryYear", {
                                                    required: showPaymentSection && "Please provide expiry year",
                                                    validate: showPaymentSection && {
                                                        minYear: (value) => !value || parseInt(value) >= 2024 || "Year should be 2024 or later",
                                                    },
                                                })}
                                            />
                                            {errors.expiryYear && (
                                                <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                                    <span>•</span> {errors.expiryYear.message}
                                                </p>
                                            )}
                                        </div>
                                        <div>
                                            <label className="block text-sm font-semibold text-slate-700 mb-2">
                                                CVV
                                            </label>
                                            <input
                                                type="text"
                                                placeholder="CVV"
                                                disabled={isLoading}
                                                className={`w-full px-4 py-2.5 border-2 rounded-lg font-inter text-sm transition-colors duration-200 focus:outline-none ${
                                                    errors.cvv
                                                        ? "border-red-400 focus:border-red-500 bg-red-50"
                                                        : "border-slate-200 focus:border-red-600 bg-white"
                                                } ${isLoading ? "opacity-60 cursor-not-allowed" : ""}`}
                                                {...register("cvv", {
                                                    required: showPaymentSection && "Please provide CVV",
                                                    pattern: showPaymentSection && {
                                                        value: /^[0-9]{3,4}$/,
                                                        message: "CVV should be 3-4 digits",
                                                    },
                                                })}
                                            />
                                            {errors.cvv && (
                                                <p className="text-red-500 text-xs font-medium mt-1.5 flex items-center gap-1">
                                                    <span>•</span> {errors.cvv.message}
                                                </p>
                                            )}
                                        </div>
                                    </div>
                                </div>
                            )}
                        </div>

                        {/* Submit button */}
                        <button
                            disabled={isLoading}
                            className="w-full bg-gradient-to-r from-red-600 to-red-700 text-white font-semibold py-2.5 rounded-lg hover:shadow-lg transition-all duration-200 disabled:opacity-70 disabled:cursor-not-allowed flex items-center justify-center gap-2 mt-6"
                            type="submit">
                            {isLoading ? (
                                <>
                                    <span className="inline-block w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin"></span>
                                    Creating Account...
                                </>
                            ) : (
                                <>
                                    <MdPersonAdd className="text-lg" />
                                    Register
                                </>
                            )}
                        </button>

                        {/* Divider */}
                        <div className="flex items-center gap-3 my-4">
                            <div className="flex-1 h-px bg-slate-200"></div>
                            <span className="text-xs text-slate-500 font-medium">Already registered?</span>
                            <div className="flex-1 h-px bg-slate-200"></div>
                        </div>

                        {/* Login link */}
                        <p className="text-center text-sm text-slate-600">
                            Already have an account?
                            <Link
                                to="/login"
                                className="font-semibold text-slate-800 hover:text-slate-900 underline transition-colors ml-1">
                                Sign In
                            </Link>
                        </p>
                    </form>
                </div>


            </div>
        </div>
    );
};

export default RegisterView;
