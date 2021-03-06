<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\Organisation;
use Yajra\DataTables\Facades\DataTables;

class OrganisationsController extends Controller
{
    public function index()
    {
        return view('admin.organisations.index');
    }

    public function datatable()
    {
        $organisations = Organisation::with('country');

        return Datatables::of($organisations)
            ->editColumn('name', function (Organisation $organisation) {
                return '<a href="' . $organisation->link() . '" class="text-bold text-info">' . $organisation->name . '</a>';
            })
            ->addColumn('fullAddress', function (Organisation $organisation) {
                return $organisation->fullAddress;
            })
            ->addColumn('action', function (Organisation $organisation) {
                return view('livewire.admin.organisation.actions', ['organisation' => $organisation]);
            })
            ->rawColumns(['action', 'name'])
            ->make(true);
    }

    public function destroy(Organisation $organisation)
    {
        $organisation->delete();

        flash()->success('Organisation has been deleted');

        return redirect()->back();
    }

}
